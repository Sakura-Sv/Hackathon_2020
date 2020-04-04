docker stop app
docker rm app
docker rmi hackathon_2020_app
git stash
git pull
mvn clean
mvn package
docker-compose build
docker-compose up -d