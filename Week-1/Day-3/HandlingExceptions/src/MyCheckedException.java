public class MyCheckedException extends Exception {

    public MyCheckedException(String errorMessage) {
        System.out.println(errorMessage);
    }
}
