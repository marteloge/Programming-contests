import Data.Array
import Control.Monad
import Text.Printf

memoize r f = (!) . listArray r . map f $ range r

solve x = f x
  where f = memoize ((0,0),x) g
        g (_,0) = 1
        g (0,_) = 0
        g (d,p) = 0.7 * f(d-1,p) + 0.2 * f(d-1,p-1) + 0.1 * f(d,p-1)

main = do
  n <- readLn
  replicateM_ n $ do
    s <- getLine
    let [a,b] = map read (words s)
    printf "%.3f\n" (solve (a,b) :: Double)
