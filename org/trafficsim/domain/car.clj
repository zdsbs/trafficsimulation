(ns org.trafficsim.domain.car)

(defstruct car :length :speed :position)

(defn brake [dec name cur-speed]
	[name (- cur-speed dec)]
)

(defn granny-speed [name speed distance]
	(if (and (not (nil? distance)) (< distance 4))
	  (brake 1 name speed)
	  [name speed]
	)
)

(defn apply-speed-function [car-with-speed-function]
	(apply (last car-with-speed-function) (butlast car-with-speed-function))
)

(defn get-all-actions [cars-with-speed-functions]
	(map apply-speed-function cars-with-speed-functions)
)

