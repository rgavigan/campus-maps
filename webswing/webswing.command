#!/bin/sh

basedir=$(dirname "$0")

cd $basedir

java -jar "${basedir}/server/webswing-jetty-launcher.jar" -j "${basedir}/jetty.properties" -serveradmin -pfa "${basedir}/admin/webswing-admin.properties" -adminctx /admin -aw "${basedir}/admin/webswing-admin-server.war"