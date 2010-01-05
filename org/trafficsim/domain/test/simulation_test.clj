(ns org.trafficsim.domain.test.simulation-test
  (:use clojure.test org.trafficsim.domain.world org.trafficsim.domain.car org.trafficsim.domain.simulation))

(deftest tick-loop-1-car-test
	(def cars-should-be [(car-with :speed 2 :head-position 12)])
	(def cars [(car-with :speed 1 :head-position 10)])
	(def cars-with-new-positions (simulation-tick cars))
	(is (= cars-should-be cars-with-new-positions)))

(deftest tick-loop-2-cars-not-in-correct-order
	(def cars-should-be [(car-with :speed 2 :head-position 22) (car-with :speed 1 :head-position 11)])
	(def cars (sort-cars-by-position [(car-with :speed 1 :head-position 10) (car-with :speed 1 :head-position 20)]))
	(def cars-with-new-positions (simulation-tick cars))
	(is (= cars-should-be cars-with-new-positions)))
	
(deftest two-ticks-two-cars
	(def cars-should-be [(car-with :speed 3 :head-position 25) (car-with :speed 2 :head-position 13)])
	(def cars0 [(car-with :speed 1 :head-position 20) (car-with :speed 1 :head-position 10)])
	(def cars1 (simulation-tick cars0))
	(def cars2 (simulation-tick cars1))
	(is (= cars-should-be cars2)))

(deftest run-sim-1-tick
	(def cars-should-be [(car-with :speed 2 :head-position 22) (car-with :speed 1 :head-position 11)])
	(def cars [(car-with :speed 1 :head-position 20) (car-with :speed 1 :head-position 10)])
	(is (= cars-should-be (run-sim 1 cars))))
	
(deftest run-sim-2-tick
	(def cars-should-be [(car-with :speed 3 :head-position 25) (car-with :speed 2 :head-position 13)])
	(def cars [(car-with :speed 1 :head-position 20) (car-with :speed 1 :head-position 10)])
	(is (= cars-should-be (run-sim 2 cars))))

(deftest run-sim-3-tick
	(def cars-should-be [(car-with :speed 4 :head-position 29) (car-with :speed 3 :head-position 16)])
	(def cars [(car-with :speed 1 :head-position 20) (car-with :speed 1 :head-position 10)])
	(is (= cars-should-be (run-sim 3 cars))))
	
(deftest persistent-state-is-kept-track-of
	(let [accum (make-accum)
 		  cars-should-be [[(car-with :speed 1 :head-position 20) (car-with :speed 1 :head-position 10)]
						 [(car-with :speed 2 :head-position 22) (car-with :speed 1 :head-position 11)]
						 [(car-with :speed 3 :head-position 25) (car-with :speed 2 :head-position 13)]]
		  cars [(car-with :speed 1 :head-position 20) (car-with :speed 1 :head-position 10)]]
		  (run-sim 2 cars (accum :update))
		  (is (= cars-should-be ((accum :get))))))

