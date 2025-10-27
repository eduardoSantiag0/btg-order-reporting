#!/bin/bash

echo "Generating Jacoco report..."
mvn clean test jacoco:report

REPORT_PATH="target/site/jacoco/index.html"

if [ ! -f "$REPORT_PATH" ]; then
    echo "Report not found in $REPORT_PATH"
    exit 1
fi

xdg-open "$REPORT_PATH"

echo "Report opened success!"