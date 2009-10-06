(ns org.trafficsim.domain.test.persistent-state-test
  (:use clojure.contrib.test-is org.trafficsim.domain.world org.trafficsim.domain.car org.trafficsim.domain.simulation))


(defn make-accum []
	(let [ c (atom[])]
		{:update #(swap! c conj %)
		 :reset #(reset! c [])
		 :get #(deref c)}))	

(deftest persistent-state-is-kept-track-of
	(let [accum (make-accum)
 		  cars-should-be [[(car-with :speed 1 :position 20) (car-with :speed 1 :position 10)]
						 [(car-with :speed 2 :position 22) (car-with :speed 1 :position 11)]
						 [(car-with :speed 3 :position 25) (car-with :speed 2 :position 13)]]
		  cars [(car-with :speed 1 :position 20) (car-with :speed 1 :position 10)]]
		  (run-sim 2 cars (accum :update))
		  (is (= cars-should-be ((accum :get))))))
	
	
