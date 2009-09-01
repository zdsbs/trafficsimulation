(ns org.trafficsim.domain.world
	(:use [org.trafficsim.domain.road] [org.trafficsim.domain.car])
)

;world knows about road and cars and their relationship

(def road {:length 1000 :width 14})

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
 	(sort #(- (:position %2) (:position %1)) cars))

(defn 
	#^{:doc "calculates the distance between a pair of cars"}
	distance-between-two-cars [[car1 car2]]
	(if (nil? car2)
		IN-FRONT
		(- (:position car1) (:length car1) (:position car2))))

(defn 
	#^{:doc "takes a set of pairs of cars, sorted, leader to the left"}
	distances-between-cars [car-pairs]
		(cons IN-FRONT (map distance-between-two-cars car-pairs)))

(defn
	#^{:doc "checks alls cars against their following distances and changes their speeds accordingly; does not modify the car, just returns the new speed"}
	all-speed-changes [cars distances]
	(map get-new-target-speed cars distances))

(defn 
	#^{:doc "applies a vector of new speeds to a vector of cars.  The vectors need to be the same size and in the same order."}
	apply-new-speeds [cars new-speeds]
	(map apply-new-speed cars new-speeds))
	
;TODO - Where does this really belong?
(defn 
	#^{:doc "determines whether a car should remain in the vector of active cars; if the car is off the end of the road remove it"}
	keep-car? [car]
	(<= (get-car-tail-position car) (road :length)))

(defn 
	#^{:doc "move all cars according to their speeds"}
	move-all-cars [input-cars]
	(filter keep-car? (map move-car input-cars)))



