(ns org.trafficsim.domain.simulation-controller
	(:use [org.trafficsim.domain.road] [org.trafficsim.domain.car] [org.trafficsim.domain.world] [org.trafficsim.domain.simulation] [org.trafficsim.domain.gui-builder])
)

(let [accum (make-accum)
	  cars [(car-with :speed 1 :head-position 27 :length 15) (car-with :speed 1 :head-position 10 :length 15)]]
	  (future (run-sim 100 cars (accum :update)))
	  (prn ((accum :get)))
	  (create-gui accum))
