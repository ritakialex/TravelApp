docker run \
  -it \
  --rm \
  --volume $PWD:/data \
  --user $(id -u):$(id -g) \
  -v /usr/share/fonts:/usr/share/fonts \
  pandoc/latex \
  --from=gfm \
  --pdf-engine=lualatex \
  -V mainfont="DejaVuSerif" \
  -V urlcolor=cyan \
  --metadata title="Ταξιδιωτική εφαρμογή σε android" \
  --highlight-style tango \
  presentasion.md \
  -o presentasion.pdf
