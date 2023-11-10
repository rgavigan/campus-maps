#!/bin/sh
#
# Startup script for the Webswing 
#
# Use following variables to override default settings:
# WEBSWING_HOME
# WEBSWING_OPTS
# WEBSWING_JAVA_HOME
# WEBSWING_JAVA_OPTS
# WEBSWING_LOG_FILE
# WEBSWING_PID_FILE
# 
# for example: 
# WEBSWING_HOME=/home/webswing WEBSWING_JAVA_HOME=/var/share/jdk8 ./webswing.sh start

export HOME=`dirname $0`
export OPTS="-h 0.0.0.0 -j $HOME/jetty.properties -c $HOME/webswing.config -pfa $HOME/admin/webswing-admin.properties -adminctx /admin"
export JAVA_HOME=$JAVA_HOME
export JAVA_OPTS=-Xmx2g
export LOG=$HOME/webswing.out
export PID_PATH_NAME=$HOME/webswing.pid

#std out logger config
MAX_OUT_LOG_SIZE=1048576000 # 100 MB
MAX_OUT_LOG_ARCHIVE_FILES=5
OUT_LOG_ARCHIVE_DIR=$HOME/logs/outarchive

if [ -n "$WEBSWING_HOME" ]; then
    HOME="$WEBSWING_HOME"
fi  
if [ -n "$WEBSWING_OPTS" ]; then
    OPTS=$WEBSWING_OPTS
fi  
if [ -n "$WEBSWING_JAVA_HOME" ]; then
    JAVA_HOME=$WEBSWING_JAVA_HOME
fi  
if [ -n "$WEBSWING_JAVA_OPTS" ]; then
    JAVA_OPTS=$WEBSWING_JAVA_OPTS
fi 
if [ -n "$WEBSWING_LOG_FILE" ]; then
    LOG=$WEBSWING_LOG_FILE
fi 
if [ -n "$WEBSWING_PID_FILE" ]; then
    PID_PATH_NAME=$WEBSWING_PID_FILE
fi 


if [ -z `command -v $0` ]; then 
    CURRENTDIR=`pwd`
    cd `dirname $0` > /dev/null
    SCRIPTPATH=`pwd`/
    cd $CURRENTDIR
else
    SCRIPTPATH="" 
fi

if [ ! -f $HOME/webswing-server.war ]; then
    echo "Webswing executable not found in $HOME folder" 
    exit 1
fi

if [ ! -f $HOME/server/webswing-jetty-launcher.jar ]; then
    echo "Webswing jetty launcher not found in $HOME/server folder" 
    exit 1
fi

if [ ! -f $JAVA_HOME/bin/java ]; then
    echo "Java installation not found in $JAVA_HOME folder" 
    exit 1
fi
if [ -z `command -v xvfb-run` ]; then
    echo "Unable to locate xvfb-run command. Please install Xvfb before starting Webswing." 
    exit 1
fi
if [ ! -z `command -v ldconfig` ]; then
    if [ `ldconfig -p | grep -i libxext.so | wc -l` -eq 0 ]; then 
        echo "Missing dependent library libXext."
        exit 1
    fi
    if [ `ldconfig -p | grep -i libxi.so | wc -l` -eq 0 ]; then
        echo "Missing dependent library libXi."
        exit 1
    fi
    if [ `ldconfig -p | grep -i libxtst.so | wc -l` -eq 0 ]; then
        echo "Missing dependent library libXtst"
        exit 1
    fi
    if [ `ldconfig -p | grep -i libxrender.so | wc -l` -eq 0 ]; then
        echo "Missing dependent library libXrender."
        exit 1
    fi
fi

is_server_running() {
    if [ -f "$PID_PATH_NAME" ]; then
        local pid=$(cat "$PID_PATH_NAME")
        if kill -0 "$pid" 2>/dev/null; then
            return 0
        else
            rm $PID_PATH_NAME
        fi
    fi
    return 1;
}


spinner() {
  i="$1"
  message="$2"
  case $(($i % 4)) in
    0) symbol="/" ;;
    1) symbol="-" ;;
    2) symbol="\\" ;;
    3) symbol="|" ;;
  esac
  printf "\r%s %s\r" "$message" "$symbol" 
}

stop_server() {
    stopping_message="$1"
    stopped_message="$2"
    failed_message="$3"

    echo "$stopping_message"

    pid=$(cat "$PID_PATH_NAME")
    kill -15 "$pid"

    for i in $(seq 1 120); do
        sleep 0.25
        if ! is_server_running; then
            break
        else
            spinner "$i" "please wait"
        fi
    done;

    printf "\r"

    if is_server_running; then
        kill -9 "$pid"  
    fi

    sleep 1
    if ! is_server_running; then
        echo "$stopped_message"
    else
        echo "$failed_message"
        exit 1
    fi
}

log_std_in() {
    mkdir -p "$OUT_LOG_ARCHIVE_DIR" #create archive dir

    size=0
    if [ -f "$LOG" ]; then
      size=$(stat -c %s "$LOG") #read file size of existing file
    fi
    
    exec 3>>$LOG #open log file

    while IFS= read -r line; do #for each line in std in
      echo "$line" >&3 #append current line
      size=$((size + ${#line} + 1))

      if [ $size -gt $MAX_OUT_LOG_SIZE ]; then #if max size reached
        for i in $(seq $MAX_OUT_LOG_ARCHIVE_FILES -1 1); do #for existing files in archive dir shift name name.1->name.2 ...
          old_file="$OUT_LOG_ARCHIVE_DIR/$(basename "$LOG").$i"
          new_file="$OUT_LOG_ARCHIVE_DIR/$(basename "$LOG").$((i + 1))"
          if [ -f "$old_file" ]; then
            if [ "$i" -eq "$MAX_OUT_LOG_ARCHIVE_FILES" ]; then
              rm -f "$old_file" 2> /dev/null
            else
              mv "$old_file" "$new_file"
            fi
          fi
        done

        archive_file="$OUT_LOG_ARCHIVE_DIR/$(basename "$LOG").1"
        
        exec 3>&- #close log
        mv "$LOG" "$archive_file"
        exec 3>$LOG #open new file

        size=0
      fi
    done
}

# See how we were called.
case "$1" in
    run)
        # Run Webswing server- expects X Server to be running
        if ! is_server_running; then
            $JAVA_HOME/bin/java $JAVA_OPTS -jar $HOME/server/webswing-jetty-launcher.jar -serveradmin -aw $HOME/admin/webswing-admin-server.war $OPTS 2>&1 | $SCRIPTPATH$0 logstdin  "$!" &
            wait $!
        else
            echo "Webswing is already running with pid $(cat $PID_PATH_NAME)"
        fi
        ;;
    start)
        # Start daemon.
        if ! is_server_running; then
            echo "Starting Webswing... "
            echo "HOME:$HOME"
            echo "OPTS:$OPTS"
            echo "JAVA_HOME:$JAVA_HOME"
            echo "JAVA_OPTS:$JAVA_OPTS"
            echo "LOG:$LOG"
            echo "PID:$PID_PATH_NAME"
            xvfb-run $SCRIPTPATH$0 run  &
            echo "Webswing STARTED"
        else
            echo "Webswing is already running with pid $(cat $PID_PATH_NAME)"
        fi
        ;;
    stop)
        if is_server_running; then
            stop_server "Webswing stoping ..." "Webswing stopped ..." "Stopping Webswing failed."
        else
            echo "Webswing is not running ..."
        fi
    ;;
    status)
        if is_server_running; then
            echo "Webswing is running with pid $(cat $PID_PATH_NAME)."
        else
            echo "Webswing is not running ..."
        fi
    ;;
    restart)
        $SCRIPTPATH$0 stop
        $SCRIPTPATH$0 start
    ;;
    savepid)
        echo $2 > $PID_PATH_NAME
        wait $(cat $PID_PATH_NAME)
    ;;
    logstdin)
        $SCRIPTPATH$0 savepid "$2"
        log_std_in
    ;;
    *)
        echo "Usage: $0 {run|start|stop|restart|status}"
        exit 1
esac

exit 0
