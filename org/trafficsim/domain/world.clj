(ns org.trafficsim.domain.world
	(:use [org.trafficsim.domain.road] [org.trafficsim.domain.car])
)

;world knows about road and cars and their relationship

(def road {:length 1000 :width 14})

(defn 
	#^{:doc "applies a new speed to a car"}
	apply-new-speed [car new-speed]
	(assoc car :speed new-speed))

(defn 
	#^{:doc "applies a vector of new speeds to a vector of cars.  The vectors need to be the same size and in the same order."}
	apply-new-speeds [cars new-speeds]
	(map apply-new-speed cars new-speeds))

(defn 
	#^{:doc "takes an ordered vector of cars and creates a vector of pairs of each car and the car in front of it"}
	ordered-car-pair [inums]
	(if (= 1 (count inums))
		[]
		(loop [nums inums pairs []]
			(if (= 2 (count nums))
				(conj pairs [(nums 0) (nums 1)])
				(recur (subvec nums 1) (conj pairs [(nums 0) (nums 1)]))))))



(defn
 	#^{:doc "sorts a vector of cars by position"}
	sort-cars-by-position [cars]
	(sort-by :position cars))

(defn 
	#^{:doc "calculates the distance between a pair of cars;  THIS IS WRONG.  It needs to take into account the size of the cars!"}
	distance-between-two-cars [[car1 car2]]
	(if (nil? car2)
		IN-FRONT
		(- (:position car1) (:position car2))))

(defn 
	#^{:doc "takes a set of pairs of cars, sorted, leader to the left"}
	distances-between-cars [car-pairs]
		(cons IN-FRONT (map distance-between-two-cars car-pairs)))

(defn 
	#^{:doc "given a car, calculates the position of the back of the car"}
	get-car-tail-position [{:keys [position length]}]
	(- position length))




(defn 
	#^{:doc "calculates the new position of a car; ; does not modify the car, just returns the new position"}
	get-new-car-head-position [{:keys [speed position]}]
	(+ position speed))

(defn 
	#^{:doc "moves the car according to its speed"}
	move-car [car]
	(assoc car :position (get-new-car-head-position car)))

(defn 
	#^{:doc "determines whether a car should remain in the vector of active cars; if the car is off the end of the road remove it"}
	keep-car? [car]
	(<= (get-car-tail-position car) (road :length)))

(defn 
	#^{:doc "move all cars according to their speeds"}
	move-all-cars [input-cars]
	(filter keep-car? (map move-car input-cars)))



(def 
	#^{:doc "a mutable sequence of the world state; each item in the sequence represents the state of the world at that time 'tick'"}
	tick-stack (ref ()))

;create the initial stat of the world by creating the first item in the tick-stack with one car
(dosync (alter tick-stack conj [(struct car 15 1 0)]))

(defn 
	#^{:doc "takes a 'tick-frame', representing the state of the world at one time, and adds it to the stack of all the ticks"}
	add-frame [tick-frame]
	(dosync (alter tick-stack conj tick-frame)))

;loops N times, moving the car for each tick and adding the new tick state to the stack of all tick states
(dotimes [tick 10] 
	(add-frame (move-all-cars (first @tick-stack))))


(defn 
	#^{:doc "randomly adds new cars to the road from time to time"}
	add-cars [car-head-positions]
	; if some criteria
	; (assoc car-head-positions (struct car 15 1) 0)
)

;(println @tick-stack)

