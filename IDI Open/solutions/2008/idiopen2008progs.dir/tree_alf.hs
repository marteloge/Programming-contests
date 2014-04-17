import qualified Data.Foldable as F
import qualified Data.Set as S
import Control.Monad
import Text.ParserCombinators.Parsec

data Tree = Node { num::Int, name::String, children::S.Set Tree }  deriving(Eq, Ord)

nodeParser = do
  id <- getState; updateState (+1)
  name <- many1 letter
  children <- between (char '(') (char ')') (nodeParser `sepBy` char ',')   <|>  return []
  return (Node id name (S.fromList children))

readTree = either (fail.show) return . runParser nodeParser 0 ""  =<<  getLine

subtrees x = S.insert x $ S.unions $ map subtrees $ S.elems (children x)

simplify s = filter (\x -> not (any (x `S.isProperSubsetOf`) s)) s

merge a b = S.fromList $ simplify [S.union x y | x <- S.elems a, y <- S.elems b]

match q tree = F.any (S.member q) (matchsets tree)
  where points = subtrees q
        matchsets n = under `S.union` self
             where under = foldl merge (S.singleton S.empty) (map matchsets (S.elems (children n)))
                   self = S.map S.singleton (S.filter possible points)
                   possible p = name n == name p && F.any (children p `S.isSubsetOf`) under

main = do
  tree <- readTree
  n <- readLn
  replicateM_ n $ do
    q <- readTree
    putStrLn $ if match q tree then "disaster" else "great success"
