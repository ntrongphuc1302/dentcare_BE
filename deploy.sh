echo "Building app..."
./mvnw clean package

echo "Deploy files to server..."
scp -r -i /Users/macbook/Desktop/test target/be.jar root@167.99.74.201:/var/www/be/

ssh -i /Users/macbook/Desktop/test root@167.99.74.201 <<EOF
pid=\$(sudo lsof -t -i :8080)

if [ -z "\$pid" ]; then
    echo "Start server..."
else
    echo "Restart server..."
    sudo kill -9 "\$pid"
fi
cd /var/www/be
java -jar be.jar
EOF
exit
echo "Done!"