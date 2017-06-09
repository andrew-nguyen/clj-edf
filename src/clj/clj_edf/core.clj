(ns clj-edf.core
  (:require [clojure.java.io :as io]
            [clojure.string :refer [trim]]

            [clj-time.format :as f])
  (:import [ru.mipt.edf EDFAnnotation
                        EDFParser
                        EDFParserResult
                        EDFSignal EDFHeader]))

;;
;; Raw wrappers around EDF4J
;;

(defn parse
  [x]
  (with-open [in (io/input-stream x)]
    (EDFParser/parseEDF in)))

;
; EDFParserResult
;

(defn header
  [^EDFParserResult r]
  (.getHeader r))

(defn signal
  [^EDFParserResult r]
  (.getSignal r))

(defn annotations
  [^EDFParserResult r]
  (.getAnnotations r))

;
; EDFHeader
;

(defn id-code
  [^EDFHeader h]
  (.getIdCode h))

(defn raw-subject-id
  "This returns the entire string (80 chars) and does not parse subfields, trim
  or anything else.

  http://www.edfplus.info/specs/edfplus.html#additionalspecs"
  [^EDFHeader h]
  (.getSubjectID h))

(defn raw-recording-id
  "This returns the entire string (80 chars) and does not parse subfields, trim
  or anything else.

  http://www.edfplus.info/specs/edfplus.html#additionalspecs"
  [^EDFHeader h]
  (trim (.getRecordingID h)))

(defn start-date
  "Returns a joda-time date/time in UTC with a time of 00:00:00"
  [^EDFHeader h]
  (let [format (f/formatter "dd.MM.yy")]
    (f/parse format (.getStartDate h))))

(defn start-time
  "Returns a joda-time date/time in UTC with a date of 1970-01-01"
  [^EDFHeader h]
  (let [format (f/formatter "HH.mm.ss")]
    (f/parse format (.getStartTime h))))

(defn start-date-time
  "Returns the combined date and time in UTC"
  [^EDFHeader h]
  (let [format (f/formatter "dd.MM.yyHH.mm.ss")]
    (f/parse format (str (.getStartDate h) (.getStartTime h)))))

(defn bytes-in-header
  [^EDFHeader h]
  (.getBytesInHeader h))

(defn format-version
  "This seems to be incorrect and should be the 44-byte reserved portion"
  [^EDFHeader h]
  (.getFormatVersion h))

(defn number-of-records
  "Number of data records (i.e., segments of signals)"
  [^EDFHeader h]
  (.getNumberOfRecords h))

(defn duration-of-records
  "Duration of each data record.  Duration of record x number of records = total number of samples"
  [^EDFHeader h]
  (.getDurationOfRecords h))

(defn number-of-signals
  "Number of signals (i.e., channels)"
  [^EDFHeader h]
  (.getNumberOfChannels h))

(defn signal-labels
  "Returns a clojure vec of trimmed labels of each signal/channel.

  Each label should be a max of 16 chars"
  [^EDFHeader h]
  (mapv trim (.getChannelLabels h)))

(defn transducer-types
  "Returns a clojure vec of trimmed transducer types of each signal/channel.

  Each label should be a max of 80 chars"
  [^EDFHeader h]
  (mapv trim (.getTransducerTypes h)))

(defn units
  "Returns a clojure vec of trimmed units of measure (also referred to as
  dimensions.

  Each label should be a max of 8 chars"
  [^EDFHeader h]
  (mapv trim (.getDimensions h)))

(defn minimum-values
  "Returns the minimum value in physical units (as returned by `units`)"
  [^EDFHeader h]
  (vec (.getMinInUnits h)))

(defn maximum-values
  "Returns the maximum value in physical units (as returned by `units`)"
  [^EDFHeader h]
  (vec (.getMaxInUnits h)))

(defn raw-minimum-values
  "Returns the minimum value in raw counts"
  [^EDFHeader h]
  (vec (.getDigitalMin h)))

(defn raw-maximum-values
  "Returns the maximum value in raw counts"
  [^EDFHeader h]
  (vec (.getDigitalMax h)))

(defn prefilterings
  [^EDFHeader h]
  (mapv trim (.getPrefilterings h)))

(defn number-of-samples
  [^EDFHeader h]
  (vec (.getNumberOfSamples h)))

;
; EDFSignal
;

(defn units-in-digit
  ""
  [^EDFSignal s]
  (.getUnitsInDigit s))

(defn raw-values
  "Returns a vector of vectors containing raw values of each signal"
  [^EDFSignal s]
  (map vec(.getDigitalValues s)))

(defn physical-values
  "Returns a vector of vectors containing physical values of each signal"
  [^EDFSignal s]
  (map vec (.getValuesInUnits s)))