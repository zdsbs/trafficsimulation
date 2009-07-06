(:import 'java.io.File)

(def current-dir (new java.io.File "./trafficsimulation/org"))

(defn get-directories [file] 
  (if (nil? file)
   []
   (filter #(. % isDirectory) (. file listFiles)))
)

(defn get-files [file]
  (if (nil? file)
    []
   (filter #(. % isFile) (. file listFiles))))

(defn print-path [files]
  (doseq [file files] 
    (if (not (nil? file))
     (println (. file getPath)))))


(defn get-all-files [file]
  (loop [dirs [file] files []]
    (if (empty? dirs)
      files 
      (recur
             (concat (rest dirs) (get-directories (first dirs))) 
             (concat files (get-files (first dirs)))
       )
)))

(println "ALL FILES")
(print-path (get-all-files current-dir))


