docker run \
  --rm \
  --volume $PWD:/data \
  --user $(id -u):$(id -g) \
  -v /usr/share/fonts:/usr/share/fonts \
  pandoc/latex \
  --from=gfm \
  --pdf-engine=lualatex \
  -V mainfont="ARIALUNI" \
  -V urlcolor=cyan \
  --highlight-style tango \
  presentasion.md \
  -o presentasion.pdf
