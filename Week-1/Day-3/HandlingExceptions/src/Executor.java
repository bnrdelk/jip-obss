public class Executor {
    public static void exec() throws MyCheckedException{
        try {
            Divider divider = new Divider();
            divider.divide();
        } catch (ArithmeticException ae)
        {
            System.out.println(ae);
            //throw new MyCheckedException("checked exc. thrown");
            throw new MyUncheckedException();
        }
    }
    
}
