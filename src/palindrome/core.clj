(ns palindrome.core
  (:gen-class))

(def usage "Usage: <start-date> <end-date>.  Where start-date and end-date should be in 'MMddyyyy' format and start-date must be before, or on, end-date.")

(defn two-args? [args]
  (= 2 (count args)))

(defn time-to-stop? [start end]
  (= 1 (compare start end)))

(defn get-date-obj [date]
  "Takes a date string and turns it into a java.util.Date"
  (.parse (java.text.SimpleDateFormat. "MMddyyyy") date))

(defn get-date-str [date]
  "Takes a java.util.Data and converts it to a string representation"
  (.format (java.text.SimpleDateFormat. "MMddyyyy") date))

(defn add-one-day [date]
  "Takes a date obj and adds one day to it."
  (java.util.Date. (+ (* 86400 1000) (.getTime date))))

(defn is-palindrome? [date]
  "Takes a date obj and determines if it is a palindrome."
  (let [date-str (get-date-str date)]
    (= date-str (clojure.string/reverse date-str)))) 

(defn find-palindromes [start end]
  "Takes a start and end dates and returns all palindromes within those bounds"
  (loop [current start
         dates []]
    (if (time-to-stop? current end) 
      dates
      (recur 
        (add-one-day current)
        (if (is-palindrome? current) 
          (conj dates (get-date-str current))
           dates)))))

(defn -main [& args]
  "A clojure attempt at SPARC's palindrome test"
  (if (two-args? args)
    (let [start (get-date-obj (first args))
          end (get-date-obj (last args))]
      (if (time-to-stop? start end)
        (println usage)
        (find-palindromes start end)))
    (println usage)))