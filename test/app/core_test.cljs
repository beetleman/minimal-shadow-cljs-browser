(ns app.core-test
  (:require [cljs.test :as t :refer [deftest testing is]]
            [app.core :as core]))

(deftest smoke
  (testing "check if app.core exports main"
    (is (fn? core/main))))
