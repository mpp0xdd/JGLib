#!/bin/bash
CFLAGS="-J-Dfile.encoding=UTF-8"
JFLAGS="-Dfile.encoding=UTF-8"
CLASSES="classes"
TEST="TestExecutor"
FORMATTER="../Lib/google-java-format-1.15.0-all-deps.jar"

usage () {
  cat 1>&2 <<EOF
    Usage: $0 [options]

    Options:
      -h      display this help
      -f      format source code
      -c      remove all class files
      -m      run compile
      -t      run test
EOF
  exit
}

format () {
  find -name '*.java' | xargs java "$JFLAGS" -jar "$FORMATTER" -i
}

clean () {
  rm -f "$CLASSES"/*.class
}

make () {
  find -name '*.java' | xargs javac "$CFLAGS" -d "$CLASSES"
}

test () {
  java "$JFLAGS" -cp "$CLASSES" -ea "$TEST"
}


if [ $# -eq 0 ]; then
  usage
fi

while getopts 'hfcmt' opt; do
  case "$opt" in
    h) usage ;;
    f) format ;;
    c) clean ;;
    m) make ;;
    t) format && make && test ;;
  esac
done