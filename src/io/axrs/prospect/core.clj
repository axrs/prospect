(ns io.axrs.prospect.core
  (:require [clojure.string :as str])
  (:import
    (clojure.lang Namespace)))

(def ^:dynamic *exit-on-not-found* true)

(defn print-synopsis [fns]
  (println "SYNOPSIS")
  (doseq [{:keys [name arglists] :as fn} fns]
    (print \tab name)
    (when (seq (first arglists))
      (print \space (str/join \space arglists)))
    (print \newline)))

(defn print-description [fns]
  (println "DESCRIPTION")
  (doseq [{:keys [name doc arglists] :as fn} fns]
    (print \tab name)
    (when (seq (first arglists))
      (print \space (str/join \space arglists)))
    (print \newline)
    (when doc
      (println \tab \tab doc))
    (doseq [arg (distinct (flatten arglists))]

      (when-let [doc (:doc (meta arg))]
        (print \tab \tab \tab arg "-" doc \newline)))
    (print \newline)))

(defn publics [ns]
  (->> (ns-interns ns)
       vals
       (filter #(and (bound? %) (fn? @%)))
       (map meta)
       (filter (comp not true? :private))
       (sort-by :name)))

(defmulti print-help (partial instance? Namespace))

(defmethod print-help true [ns]
  (print-help (publics ns)))

(defmethod print-help false [publics]
  (print-synopsis publics)
  (println)
  (print-description publics))

(defn run [ns & [f-name & args]]
  (let [f-sym (some-> f-name symbol)
        publics (publics ns)]
    (if (and f-sym (->> publics
                        (filter (comp (partial = f-sym) :name))
                        first))
      (apply (resolve f-sym) args)
      (do
        (print-help publics)
        (when *exit-on-not-found*
          (System/exit 1))))))
