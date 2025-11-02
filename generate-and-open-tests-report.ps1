Write-Host "Generating Jacoco report..."
mvn clean test jacoco:report

$reportPath = "target/site/jacoco/index.html"

if (-Not (Test-Path $reportPath)) {
    Write-Host "Report not found in $reportPath"
    exit 1
}

Start-Process $reportPath

Write-Host "Report opened successfully!"
