app_name=compojure

echo "Build and package the app"
lein ring uberjar

echo "Cleaning up current container"
docker stop $app_name
docker rm $app_name

echo "Building..."
docker build -t alexanderjamesking/$app_name .

echo "Running..."
docker run --name $app_name --detach=true -p 5000:3000 -p 5002:22 alexanderjamesking/$app_name

container_ip=`docker inspect $app_name | grep IPAddress | cut -d '"' -f 4`
echo "Container IP $container_ip"

# wait for the app to start
until $(curl --output /dev/null --silent --head --fail localhost:5000/status); do
    printf '.'
    sleep 5
done

curl localhost:5000/status

echo "$app_name is running on $container_ip"
