(ns org.trafficsim.domain.test.simulation-test
  (:use clojure.contrib.test-is org.trafficsim.domain.world org.trafficsim.domain.car org.trafficsim.domain.simulation))

(deftest tick-loop-1-car-test
	(def cars-should-be [(car-with :speed 2 :position 12)])
	(def cars [(car-with :speed 1 :position 10)])
	(def cars-with-new-positions (simulation-tick cars))
	(is (= cars-should-be cars-with-new-positions)))

(deftest tick-loop-2-cars-not-in-correct-order
	(def cars-should-be [(car-with :speed 2 :position 22) (car-with :speed 1 :position 11)])
	(def cars (sort-cars-by-position [(car-with :speed 1 :position 10) (car-with :speed 1 :position 20)]))
	(def cars-with-new-positions (simulation-tick cars))
	(is (= cars-should-be cars-with-new-positions)))
	
(deftest two-ticks-two-cars
	(def cars-should-be [(car-with :speed 3 :position 25) (car-with :speed 2 :position 13)])
	(def cars0 [(car-with :speed 1 :position 20) (car-with :speed 1 :position 10)])
	(def cars1 (simulation-tick cars0))
	(def cars2 (simulation-tick cars1))
	(is (= cars-should-be cars2)))

