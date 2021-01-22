find . -type f -iname \*.iml -delete
find . -type d -name '.gradle' -prune -exec rm -rf {} \;
find . -type d -name '.idea' -prune -exec rm -rf {} \; 
find . -type d -name 'build' -prune -exec rm -rf {} \; 
find . -type d -name 'captures' -prune -exec rm -rf {} \; 
find . -type f -iname '.gitignore' -delete 
find . -type d -name '.git' -prune -exec rm -rf {} \; 
