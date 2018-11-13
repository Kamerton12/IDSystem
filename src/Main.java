import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;

public class Main {

    static boolean isSqr(int a)
    {
        if(a == (int)Math.sqrt(a)*(int)Math.sqrt(a) )
            return true;
        return false;
    }

    public static void main(String[] args)
    {
        Random rand = new SecureRandom();
        int bitLength = 6;
        BigInteger n1 = BigInteger.probablePrime(bitLength, rand);
        BigInteger n2 = BigInteger.probablePrime(bitLength, rand);
        BigInteger n = n1.multiply(n2);


        int ni = n.intValue(), n1i = n1.intValue(), n2i = n2.intValue();

        System.out.println(ni);
        List<Integer> l = new ArrayList<>();

        f1:for(int j = 2; j < ni; j++)
        {
            for(int i = 2; i < ni; i++)
            {
                if(isSqr(j + i * ni) && j % n1i != 0 && j % n2i != 0)
                {
                    l.add(j);
                    continue f1;
                }
            }
        }
        List<Integer> l1 = new ArrayList<>(l.size());
        for(int i = 0; i < l.size(); i++)
        {
            for(int j = 2; j < ni; j++)
            {
                if(((j*l.get(i)) % ni) == 1)
                {
                    l1.add(j);
                    break;
                }
            }
        }

        List<Integer> sqrt = new ArrayList<>(l.size());
        for(int i = 0; i < l1.size(); i++)
        {
            for(int j = 0; j < ni; j++)
            {
                if((j*j)%ni == l1.get(i))
                {
                    sqrt.add(j);
                    break;
                }
            }
        }


        int boolLength = 5;
        int k = 8;

        List<Integer> publicKey = new ArrayList<>();
        List<Integer> privateKey = new ArrayList<>();
        for(int i = 0; i < boolLength; i++)
        {
            int p = Math.abs(rand.nextInt())%l.size();
            publicKey.add(l.get(p));
            privateKey.add(sqrt.get(p));
        }

        for(int pp = 0; pp < k; pp++)
        {
            BigInteger r = new BigInteger(bitLength, rand);
            BigInteger x = r.pow(2).mod(n);

            //B-side
            boolean[] b = new boolean[boolLength];
            for(int i = 0; i < boolLength; i++)
                b[i] = rand.nextBoolean();

            //A-side
            BigInteger y = r;
            for(int i = 0; i < boolLength; i++)
            {
                if(b[i])
                    y = y.multiply(BigInteger.valueOf(privateKey.get(i))).mod(n);
            }

            //B-side
            BigInteger xx = y.pow(2);
            for(int i = 0; i < boolLength; i++)
            {
                if(b[i])
                    xx = xx.multiply(BigInteger.valueOf(publicKey.get(i))).mod(n);
            }
            if(x.compareTo(xx) == 0)
                System.out.println("Success");
            else
                System.out.println("Failure");
        }
    }
}
