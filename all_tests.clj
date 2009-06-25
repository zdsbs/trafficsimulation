(ns all-tests
  (:use clojure.contrib.test-is 
    org.trafficsim.domain.test.road-test
))

(clojure.contrib.test-is/run-tests 'org.trafficsim.domain.test.road-test)
