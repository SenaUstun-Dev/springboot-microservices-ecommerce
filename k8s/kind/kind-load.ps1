# kind-load.ps1
Write-Host "Pulling images..."

$images = @(
    "mongo:8.0",
    "mysql:8.4.0",
    "postgres:16",
    "quay.io/keycloak/keycloak:26.0.8",
    "confluentinc/cp-kafka:7.8.0",
    "confluentinc/cp-schema-registry:7.8.0",
    "provectuslabs/kafka-ui:latest",
    "grafana/loki:3.7.0",
    "prom/prometheus:v3.1.0",
    "grafana/tempo:2.10.3",
    "grafana/grafana:11.5.2",
    "senaustundev/eureka-server:latest",
    "senaustundev/product-service:latest",
    "senaustundev/order-service:latest",
    "senaustundev/inventory-service:latest",
    "senaustundev/notification-service:latest",
    "senaustundev/api-gateway:latest"
)

foreach ($image in $images) {
    Write-Host "Pulling $image"
    docker pull $image
}

Write-Host "Loading images into kind cluster..."

foreach ($image in $images) {
    Write-Host "Loading $image"
    kind load docker-image $image --name microservices
}

Write-Host "Done!"
