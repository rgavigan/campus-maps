cd %~dp0
java -jar server/webswing-jetty-launcher.jar -j jetty.properties -serveradmin -pfa admin/webswing-admin.properties -adminctx /admin -aw admin/webswing-admin-server.war