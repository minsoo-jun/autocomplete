### Autocomplete API

### Deploy memo
```
docker build -t  asia.gcr.io/${PROJECT_ID}/autocomp-nginx:1.0.8 .
docker push asia.gcr.io/${PROJECT_ID}/autocomp-nginx:1.0.8

gcloud config set project ${{ PROJECT_ID }}
gcloud config set compute/zone asia-northeast1-b
gcloud container clusters get-credentials ec-cluster-2
#Spring Boot
kubectl run autocomplete --image=asia.gcr.io/${PROJECT_ID}/autocomplete:1.0.1 --port 8080
kubectl expose deployment autocomplete --type=LoadBalancer --port 8080 --target-port 8080
#Nginx
kubectl run autocomp-nginx --image=asia.gcr.io/${PROJECT_ID}/autocomp-nginx:1.0.8 --port 80
kubectl expose deployment autocomp-nginx --type=LoadBalancer --port 80 --target-port 80

# file contents check
kubectl exec -it autocomp-nginx-5d5cbfff5d-w9c59 -- ls /usr/share/nginx/html -al
```