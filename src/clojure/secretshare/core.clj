(ns secretshare.core
  (:import [com.tiemens.secretshare.engine.SecretShare]))


(defn prime384 []
  (com.tiemens.secretshare.engine.SecretShare/getPrimeUsedFor384bitSecretPayload))

(defn prime192 []
  (com.tiemens.secretshare.engine.SecretShare/getPrimeUsedFor192bitSecretPayload))

(defn prime4096 []
  (com.tiemens.secretshare.engine.SecretShare/getPrimeUsedFor4096bigSecretPayload))

(defn public-info
  ([n k description]
   (public-info n k (prime384) description))

  ([n k]
   (public-info n k (prime384) nil))
  ([]
   (public-info 2 2))
  ([ n k m description]
    (com.tiemens.secretshare.engine.SecretShare$PublicInfo. n k m description)
   ))


(defn ss
  ([pi]
   (com.tiemens.secretshare.engine.SecretShare. pi)))

(defn si->map [si]
  (let [pi (.getPublicInfo si)]
    {:index (.getIndex si)
     :share (.getShare si)
     :n (.getN pi)
     :k (.getK pi)
     :mod (.getPrimeModulus pi)
     :description (.getDescription pi)}))

(defn map->si [m]
  (com.tiemens.secretshare.engine.SecretShare$ShareInfo. (:index m) (:share m)
                                                         (com.tiemens.secretshare.engine.SecretShare$PublicInfo.
                                                          (:n m)
                                                          (:k m)
                                                          (:mod m)
                                                          (:description m))))

(defn split
  ([pi data]
   (map si->map
        (.getShareInfos
         (.split (ss pi) data))))
  ([data]
   (split (public-info) data)))


(defn combine
  ([shares]
   (let [shares (map map->si shares)
         pi     (.getPublicInfo (first shares))]
     (.getSecret
       (.combine (ss pi) (vec shares))))))

