docker stop app
docker rm app
docker rmi hackathon_2020_app
git stash
git pull
chmod +x appUpdate.sh
mvn clean
mvn package
docker-compose build
docker-compose up -d

# app自动更新脚本
# maintainer: LiuZhen