port="/dev/$(rfcomm | tail -n 1 | cut -d':' -f1)"

PORT=$port lein run
