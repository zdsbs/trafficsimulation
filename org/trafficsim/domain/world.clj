(ns org.trafficsim.domain.world
	(:use [org.trafficsim.domain.road] [org.trafficsim.domain.car])
)

;world knows about road and cars and their relationship

(def road {:length 1000 :width 14})

(defn apply-new-speed [car new-speed]
	(assoc car :speed new-speed))

(defn apply-new-speeds [cars new-speeds]
	(map apply-new-speed cars new-speeds))

(defn pair [inums]
	(if (= 1 (count inums))
		[]
		(loop [nums inums pairs []]
			(if (= 2 (count nums))
				(conj pairs [(nums 0) (nums 1)])
				(recur (subvec nums 1) (conj pairs [(nums 0) (nums 1)]))))))



(defn sort-cars-by-position [cars]
	(sort-by :position cars))

(defn distance-between-two-cars [[car1 car2]]
	(if (nil? car2)
		IN-FRONT
		(- (:position car1) (:position car2))))

(defn get-car-tail-position [{:keys [position length]}]
	(- position length))



;position after the next tick
(defn get-new-car-head-position [{:keys [speed position]}]
	(+ position speed))

(defn move-car [car]
	(assoc car :position (get-new-car-head-position car)))

(defn keep-car? [car]
	(<= (get-car-tail-position car) (road :length)))

(defn move-all-cars [input-cars]
	(filter keep-car? (map move-car input-cars)))



(def tick-stack (ref ()))

(dosync (alter tick-stack conj [(struct car 15 1 0)]))

(defn add-frame [tick-frame]
	(dosync (alter tick-stack conj tick-frame)))

(dotimes [tick 10] 
	(add-frame (move-all-cars (first @tick-stack))))


(defn add-cars [car-head-positions]
	; if some criteria
	; (assoc car-head-positions (struct car 15 1) 0)
)

;(println @tick-stack)

