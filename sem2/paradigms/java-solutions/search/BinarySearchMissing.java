package search;

public class BinarySearchMissing {
    //Pred  forall i in 0..arr.length arr[i+1] >= arr[i]
    public static void main(String[] args) {
        if (args.length < 1) {
            // args.length <= 1
            System.out.println(-1);
            return;
        }
        // args.length >= 1 &&   forall i in 0..args.length-1 args[i] <= args[i+1]
        int[] arr = stringArrayToInt(args);
        int elem = Integer.parseInt(args[0]);
        //int ans = iterative(arr, elem);
        int ans = recursive(arr, elem);
        //-args.length <= ans < args.length &&  args[ans + 1] >= elem &&
        // (args[ans] < elem || ans==0)
        System.out.println(ans);

    }

    //Pred stringArr.length > 0
    //Post forall x:  R[x-1] = int(stringArr[x]) && R.length == stringArr.length-1
    private static int[] stringArrayToInt(String[] stringArr) {
        //stringArr.length > 0
        int[] arr = new int[stringArr.length - 1];
        // arr.length = stringArr.length - 1

        //I forall x in 0 .. i'  arr[x-1] = int(stringArr[x])
        for (int i = 1; i < stringArr.length; i++) {
            // 1 <= i' < stringArr.length
            arr[i - 1] = Integer.parseInt(stringArr[i]);
            // arr[i'-1] = int(stringArr[i'])
        }
        return arr;
        //forall x:  R[x-1] = int(stringArr[x]) &&
        // R.length == stringArr.length-1
    }

    //Pred  forall i in 0..arr.length arr[i+1] >= arr[i]
    //Post (arr.length>R>=0 &&  arr[R] == elem && arr[R-1] !=elem)|| (-arr.length<R<0 && arr[-R+1] < elem  && arr[-R+2] >= elem)
    public static int iterative(int[] arr, int elem) {
        //True
        int start = -1;
        int end = arr.length;
        int index = 0;
        // start = -1 ,index = 0, end = arr.length

        //I start' < index' < end' && arr[index] >= arr[start']  && forall  x  in 0..start arr[x] <= elem &&
        // forall  x  in end..arr.length arr[x] >= elem
        while (end - start > 1) {
            // end' - start' > 1
            index = (start + end) / 2;
            //start' <= index' <= end' &&
            // forall i in 0..arr.length arr[i+1] >= arr[i]
            if (arr[index] < elem) {
                //arr[index'] < elem && start' < index' < end'
                start = index;
                //start' == index' &&
                // forall  x  in 0..start arr[x] < elem
            } else {
                //arr[index']>= elem && start' < index' < end'
                end = index;
                //end' == index' &&
                //forall  x  in end..arr.length arr[x] >= elem
            }
            //start' <= index' <= end'  &&  forall  x  in 0..start arr[x] < elem &&
            //forall  x  in end..arr.length arr[x] >= elem
        }
        //  ((start' <= index' < end')|(start' < index' <= end')-> end' - start' <=  1 )&&
        //(arr.length>end'>=0 &&  arr[end'] == elem && arr[end'-1] !=elem)|| (-arr.length<end'<0 && arr[-end'+1] < elem  && arr[-end'+2] >= elem)
        //  forall i in 0..index arr[i] > elem
        if (end < arr.length && arr[end] == elem) {
            // arr[end']==elem' && end'!=arr.length
            //-> arr[end'+1] > elem
            return end;
        } else {
            // arr[end']==elem' || end'!=arr.length
            //-> arr not contains elem
            return -end - 1;
        }
    }

    //Pred forall i in 0..args.length  arr[i+1] < arr[i]
    //Post (arr.length>R>=0 &&  arr[R] == elem && arr[R-1] !=elem)|| (-arr.length<R<0 && arr[-R+1] < elem  && arr[-R+2] >= elem)
    public static int recursive(int[] arr, int elem) {
        return rec(arr, -1, arr.length, elem);
        //(arr.length>R>=0 &&  arr[R] == elem && arr[R-1] !=elem)|| (-arr.length<R<0 && arr[-R+1] < elem  && arr[-R+2] >= elem)
    }

    //Pred forall i in 0..args.length arr[i+1] < arr[i] &&   -1 <= start < end <= arr.length
    //Post (start <= R < end)||(-start >= R > -end) &&
    //(end>R>=start &&  arr[R] == elem && arr[R-1] !=elem)|| (-start<=R<-end && arr[-R+1] < elem  && arr[-R+2] >= elem)
    private static int rec(int[] arr, int start, int end, int elem) {
        if (end - start > 1) {
            //end-start > 1 && (-1 <= start < end <= arr.length)
            int index = (start + end) / 2;
            //0 <= start < index < arr.length

            if (arr[index] < elem) {
                //arr[index] > elem
                return rec(arr, index, end, elem);
                //(arr.length>R>=0 &&  arr[R] == elem && arr[R-1] !=elem)|| (-arr.length<R<0 && arr[-R+1] < elem  && arr[-R+2] >= elem)
            } else {
                //arr[index] <= elem
                return rec(arr, start, index, elem);
                //(arr.length>R>=0 &&  arr[R] == elem && arr[R-1] !=elem)|| (-arr.length<R<0 && arr[-R+1] < elem  && arr[-R+2] >= elem)
            }
        }
        if (end < arr.length && arr[end] == elem) {
            // arr[end']==elem' && end'!=arr.length
            //-> arr[end'+1] > elem
            return end;
        } else {
            // arr[end']==elem' || end'!=arr.length
            //-> arr not contains elem
            return -end - 1;
        }
        //end-start<=1
        //  forall i in index..arr.length arr[i] <= elem &&
        //  forall i in 0..index arr[i] > elem


    }
}
