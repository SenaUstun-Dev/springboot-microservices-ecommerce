# create-kind-cluster.ps1
Write-Host "=== Starting Kind Cluster ==="

kind create cluster --name microservices --config kind-config.yaml

Write-Host "Loading Docker Images into Kind Cluster..."
.\kind-load.ps1

Write-Host "=== Kind Cluster Started ==="
