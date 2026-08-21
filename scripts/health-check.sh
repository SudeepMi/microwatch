#!/usr/bin/env bash
echo "Checking MicroWatch Gateway & Backend Status..."
curl -s http://localhost:8080/api/v1/dashboard/summary | jq . || curl -s http://localhost:8080/api/v1/dashboard/summary
