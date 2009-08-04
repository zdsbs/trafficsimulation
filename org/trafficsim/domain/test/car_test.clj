(ns org.trafficsim.domain.test.car-test
  (:use clojure.contrib.test-is org.trafficsim.domain.car))

(deftest brake-test
	(is (= [:a 1] (brake 1 :a 2)))
)


(deftest speed-change-test
	(is (= [:a 4] (speed-change [{:decel-dist 2 :accel-dist 10} :a 5 1])))
	(is (= [:a 5] (speed-change [{:decel-dist 2 :accel-dist 10} :a 5 5])))
	(is (= [:a 6] (speed-change [{:decel-dist 2 :accel-dist 10} :a 5 11]))))

(deftest collect-all-new-speed-changes-test
	(is (= [[:a 4] [:b 3] [:c 5]] 
		(collect-all-new-speed-changes 
			[[{:decel-dist 5 :accel-dist 7} :a 4 6] 
			[{:decel-dist 4 :accel-dist 5} :b 4 3] 
			[{:decel-dist 2 :accel-dist 10} :c 4 IN-FRONT]])))
	(is (= [] (collect-all-new-speed-changes []))))


;some pseudo-code and design thoughts....

;def car-map {
;:name :a {:speed 2 :pos 3 :braking-strategy granny-speed}
;:name :b {:speed 2 :pos 3 :braking-strategy granny-speed}
;:name :c {:speed 2 :pos 3 :braking-strategy granny-speed}
;:name :d {:speed 2 :pos 3 :braking-strategy granny-speed}
;}


;(def actions (get-all-actions (transform-to-relative-distance all-cars))

;(update-position all-cars)
;(def actions (get-all-actions all-cars))
;(update-speed actions)

