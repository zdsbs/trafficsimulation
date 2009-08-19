(ns org.trafficsim.domain.world
	(:use [org.trafficsim.domain.road] [org.trafficsim.domain.car])
)

;world knows about road and cars and their relationship

(def road {:length 1000 :width 14})


;(defn foo [[name length position] [name length position]]
;	[]
;)

(defn sort-cars-by-position [cars]
	(sort-by :position cars))

(defn distance-between-two-cars [car1 car2]
	(- (:position car2) (:position car1)))



(defn get-car-tail-position [{:keys [position length]}]
	(- position length))

;position after the next tick
(defn get-new-car-head-position [{:keys [speed position]}]
	(+ position speed))

(defn get-car-new-position [car]
	(assoc car :position (get-new-car-head-position car)))

(defn keep-car? [car]
	(<= (get-car-tail-position car) (road :length)))

(defn get-all-cars-new-positions [input-cars]
	(filter keep-car? (map get-car-new-position input-cars)))



(def tick-stack (ref ()))

(dosync (alter tick-stack conj [(struct car 15 1 0)]))

(defn add-frame [tick-frame]
	(dosync (alter tick-stack conj tick-frame)))

(dotimes [tick 10] 
	(add-frame (get-all-cars-new-positions (first @tick-stack))))


(defn add-cars [car-head-positions]
	; if some criteria
	; (assoc car-head-positions (struct car 15 1) 0)
)

;(println @tick-stack)

