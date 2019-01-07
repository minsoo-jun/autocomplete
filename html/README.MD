docker build -t  asia.gcr.io/${PROJECT_ID}/autocomp-nginx:1.0.14 .
docker push asia.gcr.io/${PROJECT_ID}/autocomp-nginx:1.0.14

docker build -t  asia.gcr.io/${PROJECT_ID}/autocomplete:1.0.6 .
docker push asia.gcr.io/${PROJECT_ID}/autocomplete:1.0.6

gcloud config set project minsoojun-222707
gcloud config set compute/zone asia-northeast1-b
gcloud container clusters get-credentials ec-cluster-2
#Spring Boot
kubectl run autocomplete --image=asia.gcr.io/${PROJECT_ID}/autocomplete:1.0.5 --port 8080
kubectl expose deployment autocomplete --type=LoadBalancer --port 8080 --target-port 8080
#Nginx
kubectl run autocomp-nginx --image=asia.gcr.io/${PROJECT_ID}/autocomp-nginx:1.0.13 --port 80
kubectl expose deployment autocomp-nginx --type=LoadBalancer --port 80 --target-port 80