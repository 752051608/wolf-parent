-------------------------*******神提醒,不要过度相信docker仓库的镜像,有些镜像是有问题的***-------------------------

-------------------------创建网段-------------------------

docker network create  --subnet=172.18.0.0/16  dev-net

-------------------------自定义ip-------------------------
step0:相应配置文件 统一放置在 /data/k8s/configs/dev
step1: redis---172.18.0.79    activemq---172.18.0.61
       platform---172.18.0.3  site---172.18.0.2
step2: 在/data/k8s/configs/dev修改相应的配置ip 且端口指定为默认端口
       若用宿主机ip,需要指定映射端口。

-------------------------redis-------------------------
docker pull redis:5.0

docker run  -itd -e TZ="Asia/Shanghai"  -p 6380:6379  \
--net dev-net --ip 172.18.0.79  \
-v /data/k8s/configs/dev/redis:/etc/redis \
-v /data/k8s/data/dev/redis:/data \
--name dev-redis redis:5.0 \
redis-server /etc/redis/redis.conf

-------------------------amq-------------------------
step1: docker search activemq
step2: docker pull rmohr/activemq
step3: docker tag rmohr/activemq activemq:tag0
备注：神坑  类似tomcat的坑,比tomcat更坑
开始用的镜像webcenter/activemq,项目platform和site启动没问题,使用时报错,很难排查。
后面更改为镜像：rmohr/activemq,发现成功啦。

docker run  -itd \
--restart=always \
-w /opt/activemq \
-v /data/k8s/configs/dev/activemq:/opt/activemq/conf \
--net dev-net \
--ip 172.18.0.61 \
-p11883:1883 \
-p18161:8161 \
-p16163:61613 \
-p11614:61614 \
-e TZ="Asia/Shanghai"  \
--name dev-activemq \
activemq:tag0 

-------------------------platform-------------------------
step1:进入/data/k8s/apps/dev
step2:打镜像 docker build  -t dev-platform-image .

docker run -itd \
        --restart=always \
        --net dev-net \
        --ip 172.18.0.3 \
        -e TZ="Asia/Shanghai" \
        -v /data/k8s/apps/dev/platform-server.jar:/app/platform_java/platform-server.jar \
        -v /data/k8s/logs/dev/platform:/app/log/platform  \
        -v /data/k8s/configs/dev/platform:/app/platform_java/config  \
        -v /data/k8s/configs/dev/jdk:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/security/  \
        --name dev-platform \
        -P \
        dev-platform-image
备注:site项目用的tomcat7 Jdk8

-------------------------site-------------------------
step1:docker search tomcat7
step2:docker pull picoded/tomcat7
step3:docker tag picoded/tomcat7 t7j8:tag0
备注:site项目用的tomcat7 Jdk8,踩坑历程---部分镜像运行tomcat存在问题,譬如maluuba/tomcat7-java8无法启动。

docker run   -itd \
--net dev-net \
--ip 172.18.0.2 \
-w /usr/local/tomcat/ \
-e TZ="Asia/Shanghai" \
-v /data/k8s/logs/dev/site:/usr/local/tomcat/logs \
-v /data/k8s/apps/dev/site:/usr/local/tomcat/site/  \
-v /data/k8s/apps/dev/web-xml.d:/usr/local/tomcat/webapps/manager/WEB-INF/ \
-v /data/k8s/configs/dev/site/boss:/usr/local/tomcat/site/boss/WEB-INF/classes/com/moyu/web/config \
-v /data/k8s/configs/dev/site/third:/usr/local/tomcat/site/third/WEB-INF/classes/com/moyu/web/config \
-v /data/k8s/configs/dev/tomcat/:/usr/local/tomcat/conf/ \
-v /data/k8s/configs/dev/cert/wechat/:/data/cert/wechat/ \
--privileged=true \
--name dev-site \
-P \
t7j8:tag0

--restart=always \

-------------------------mysql--目前使用阿里云,暂时不需要-------------------------
docker  run -itd --name mysql -p3307:3306 \
--net redis-net --ip 172.18.0.36 \
-e MYSQL_USER=august \
-e MYSQL_PASSWORD=123456 \
-e MYSQL_ROOT_PASSWORD=123456 \
-v /data/k8s/mysql/data/:/var/lib/mysql/ \
-v /data/k8s/mysql/conf/mysqld.cnf:/etc/mysql/mysql.conf.d/mysqld.cnf \
-v /data/k8s/mysql/log/:/var/log/mysql \
mysql:5.7

-------------------------nginx-------------------------
step1:修改相应配置ip,服务器进行访问





-------------------------------------platform重新发布步骤---------------------------------------------

step1: cd /data/k8s/build/ && ./dev_build.sh
step2: 选择 moyu-server-platform
step3: 输入分支代码 回车 
step4: q 
step5: docker rm -f dev-platform
step6: 
        docker run -itd \
                --restart=always \
                --net dev-net \
                --ip 172.18.0.3 \
                -e TZ="Asia/Shanghai" \
                -v /data/k8s/apps/dev/platform-server.jar:/app/platform_java/platform-server.jar \
                -v /data/k8s/logs/dev/platform:/app/log/platform  \
                -v /data/k8s/configs/dev/platform:/app/platform_java/config  \
                -v /data/k8s/configs/dev/jdk:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/security/  \
                --name dev-platform \
                -P \
                dev-platform-image

step7: 查看日志 docker logs -f dev-platform 或者进入目录 /data/k8s/logs/dev/platform查看日志

-------------------------------------site后端重新发布---------------------------------------------

step1: cd /data/k8s/build/ && ./dev_build.sh
step2: 选择 moyu-server-site  回车
step3: 输入分支代码 回车
step4: docker restart dev-site

查看日志：docker logs -f dev-site 或者 tail -fn 100 /data/k8s/logs/dev/site/catalina*log

看到日志输出 INFO: Server startup in xxx ms 说明启动成功

登陆账号 https://dev.moyuxls.com/newboss


-------------------------------------site前端重新发布---------------------------------------------
newboss发布
step1: cd /data/k8s/build/ && ./dev_build.sh
step2: 选择 moyu-vue-boss 回车
step3: 输入分支代码 回车
step4: 输入 q 回车
newthird发布
step1: cd /data/k8s/build/ && ./dev_build.sh
step2: 选择 moyu-vue-third 回车
step3: 输入分支代码 回车
step4: 输入 q 回车


-------------------------------------常用docker命令---------------------------------------------

启动docker : systemctl start docker

查看容器(服务) : docker ps -a

启动/停止/重启 服务                 site | redis | activemq | platform
docker  start/stop/restart        dev-site | dev-redis | dev-activemq | dev-platform 

比如重启platform
docker restart dev-platform



备注：
ActiveMq使用说明:
1）activemq管控台使用jetty部署,客户端访问用户名 密码,目前默认admin admin
   /conf/jetty-realm.properties
   访问地址 http://127.0.0.1:8161/admin (ip 对应服务器ip)
2）activemq应该设置有安全连接认证机制，只有符合认证的用户才可以发送和接收消息，
   /conf/activemq.xml添加下面配置,放在broker节点里面,
   ﻿<plugins>
   			<simpleAuthenticationPlugin>
   				<users>
   					<authenticationUser username="admin" password="bWYkxVC74QAeRS5z" groups="users,admins"/>
   				</users>
   			</simpleAuthenticationPlugin>
   	</plugins>
   	
   	开启延迟队列：
   	<broker xmlns="http://activemq.apache.org/schema/core" brokerName="localhost" dataDirectory="${activemq.data}" schedulerSupport="true">
   	后面加上 schedulerSupport="true"


