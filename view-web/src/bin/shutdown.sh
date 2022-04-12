#! /bin/sh
name="yr-yinxx-1.0.0"
pid=${name}".pid"
echo -n "stop $name ..."
if [ -f "$pid" ]
    then
        PID=$(cat ${pid})
            kill -9 $PID
        [ $? -eq 0 ] && echo  "[ok]"
        rm -fr $pid
    else
        echo  "[ok]"
 fi
