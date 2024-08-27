import java.util.List;

@FunctionalInterface
public interface ListFilter {
    boolean satisfiesCondition(String a, List<String> list);
}
