import Data.Maybe
import qualified Data.Set as S
import qualified Data.Map as M
import Data.List
import Data.Bits
import Text.ParserCombinators.Parsec
import Control.Monad

-- each expression is just an int (one bit per possible input)
-- element i of 'available' is the set of expressions that cost at most i
-- 'news' are the expressions that require exactly i characters
-- 'canMake' are the ones of length i that can be built from optimal shorter ones (but are not necessarily optimal themselves)

vars @ [vX, vY, vZ] = [0x55, 0x33, 0x0f]; true = 0xff

available, canMake, news :: [S.Set Int]

available = scanl1 S.union canMake
news = zipWith S.difference canMake (S.empty : available)

canMake = S.empty : S.fromList vars : map f [2..]
  where f n = S.fromList (andors ++ nots)
                   where nots = map (true -) (li (n-1))
                         andors = concatMap withLeftCost [1..n-4]
                         withLeftCost i = concat [[a .&. b, a .|. b]  | a <- li i, b <- li (n-3-i)]
                         li x = S.elems (news !! x)

mapOf list = M.fromList $ concat $ zipWith f [0..] list  where f i n = map (flip (,) i) (S.elems n)

expr = op '&' (.&.)  
   <|> op '|' (.|.)
   <|> (char '!' >> expr >>= return . (true -))
   <|> (char 'x' >> return vX)
   <|> (char 'y' >> return vY)
   <|> (char 'z' >> return vZ)
        where op c o = try $ do { char '('; a <- expr; char ' '; char c; char ' '; b <- expr; char ')'; return (o a b) }
                        
main = do
  let m = mapOf (take 50 news)
  n <- readLn
  replicateM_ n $ do
    s <- getLine
    putStrLn $ either show (show . ((M.!) m)) (parse expr s s)
