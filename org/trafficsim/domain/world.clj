(ns org.trafficsim.domain.world
	(:use [org.trafficsim.domain.road] [org.trafficsim.domain.car])
)

;world knows about road and cars and their relationship

(def road {:length 1000 :width 14})

(def cars [])

(defn get-car-tail-position [{:keys [position length]}]
	(- position length))

;position after the next tick
(defn get-new-car-head-position [{:keys [speed position]}]
	(+ position speed))

(defn get-car-new-position [car]
	(assoc car :position (get-new-car-head-position car)))

(defn get-all-cars-new-positions [input-cars]
	(map get-car-new-position input-cars))



(defn add-cars [car-head-positions]
	; if some criteria
	; (assoc car-head-positions (struct car 15 1) 0)
)




