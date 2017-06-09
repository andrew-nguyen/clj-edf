# clj-edf

A wrapper library around [EDF4J](https://github.com/MIOB/EDF4J) for
interacting with EDF (European Data Format) files

## Usage

```clojure
(require '[clj-edf.core :as c])

(let [f (c/parse "somefile.edf")
      header (c/header f)
      signal (c/signal f)]
  (print (c/raw-subject-id header)) ; raw string corresponding to the subject id field
  (print (c/physical-vales (c/signal signal))) ; vector of vectors of physical/physiological values
  (print (-> signal c/signal c/physical-values first)) ; vector of physical values of first signal
  )
```

## License

Copyright © 2017 Andrew Nguyen

Distributed under the MIT License