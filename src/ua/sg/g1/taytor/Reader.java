package ua.sg.g1.taytor;

import java.util.ArrayList;

class Reader {
    private int  sizeof;
    private int operations;
    private String []names = new String[20];
    private ArrayList<Character> result = new ArrayList<>();
    private ArrayList<Character> steck = new ArrayList<>();

    private int index=0;

    void setNumber(int number){
        this.sizeof=number;
    }
    int getNumber(){
        return this.sizeof;
    }
    void post(char[] str) {
        int i = 0;
        while (i < str.length) {
            if (str[i] >= 'a' && str[i] <= 'z') result.add(str[i]);
            else {
                if (steck.size() == 0 || steck.get(steck.size() - 1) == '(' || str[i] == '(') steck.add(str[i]);
                else if (priority(str[i]) > priority(steck.get(steck.size() - 1))) steck.add(str[i]);
                else if (str[i] == ')') {
                    for (int j = steck.size() - 1; j >= 0; j--) {
                        if (steck.get(j) == '(') {
                            steck.remove(j);
                            break;
                        } else {
                            result.add(steck.get(j));
                            steck.remove(j);
                        }
                    }
                } else {
                    if (priority(str[i]) < priority(steck.get(steck.size() - 1))) {
                        for (int j = steck.size() - 1; j >= 0; j--) {
                            if (priority(steck.get(j)) < priority(str[i])) break;
                            if (steck.get(j) == '(') break;
                            result.add(steck.get(j));
                            steck.remove(j);
                        }
                        steck.add(str[i]);
                    }
                }
            }
            i++;
        }

        for (int j = steck.size() - 1; j >= 0; j--) {
            result.add(steck.get(j));
        }
        result.forEach(System.out::print);
        operations=countIndex();
    }
    private int priority(char el) {
        if (el == '!') return 6;
        else if (el == '*') return 5;
        else if (el == '+') return 4;
        else if (el == '/') return 3;
        else if (el == '-') return 2;
        else if (el == '(' || el == ')') return 1;

        return 0;
    }
    private ArrayList<Character> cloneList(ArrayList<Character> list) {
        ArrayList<Character> clone = new ArrayList<Character>(list.size());
        for(Character item: list) clone.add(item);
        return clone;
    }
    private ArrayList<Character> toNumbers(int k){
        ArrayList<Character> resultCopy = cloneList(result);
        int len=getNumber();
        char a = 'a';
        char b = 'b';
        char c = 'c';
        char d = 'd';
        char e = 'e';
        int [][] val_in=perestanovki().clone();
        char [][] val=new char[(int)Math.pow(2, len)][len];
        for (int i = 0; i < (int)Math.pow(2, len); i++) {
            for (int j = 0; j < len; j++) {
                if(val_in[i][j]==1) val[i][j]='1'; else val[i][j]='0';
            }
        }

        for (int i = 0; i <= resultCopy.size() - 1; i++) {
            if (resultCopy.get(i) == a) resultCopy.set(i, val[k][0]);
            if (resultCopy.get(i) == b) resultCopy.set(i, val[k][1]);
            if (resultCopy.get(i) == c) resultCopy.set(i, val[k][2]);
            if (resultCopy.get(i)== d) resultCopy.set(i,  val[k][3]);
            if (resultCopy.get(i)== e) resultCopy.set(i,  val[k][4]);
        }
        return resultCopy;
    }
    String [][]getresul() {
        String [][]value = new String[(int)Math.pow(2,getNumber())][operations];
        int len=(int)Math.pow(2,getNumber());

        for (int k = 0; k < len ; k++) {
            ArrayList<Character> resultCopy = cloneList(toNumbers(k));
            for (int i = 0; i <= resultCopy.size() - 1; i++) {
                switch (resultCopy.get(i)) {
                    case '!':
                        if (resultCopy.get(i - 1) == '0') {
                            resultCopy.set(i - 1, '1');
                            value[k][index]=resultCopy.get(i-1).toString();
                            resultCopy.remove(i);
                        } else {
                            resultCopy.set(i - 1, '0');
                            value[k][index]=resultCopy.get(i-1).toString();
                            resultCopy.remove(i);
                        }
                        index++;
                        i=0;
                        break;
                    case '*':
                        if (resultCopy.get(i - 2) == '1' && resultCopy.get(i - 1) == '1') {
                            resultCopy.set(i, '1');
                            value[k][index]=resultCopy.get(i).toString();
                            resultCopy.remove(i - 2);
                            resultCopy.remove(i - 2);
                        } else {
                            resultCopy.set(i, '0');
                            value[k][index]=resultCopy.get(i).toString();
                            resultCopy.remove(i - 2);
                            resultCopy.remove(i - 2);
                        }
                        index++;
                        i=0;
                        break;
                    case '+':
                        if (resultCopy.get(i - 2) == '1' || resultCopy.get(i - 1) == '1') {
                            resultCopy.set(i, '1');
                            value[k][index]=resultCopy.get(i).toString();
                            resultCopy.remove(i - 2);
                            resultCopy.remove(i - 2);

                        } else {
                            resultCopy.set(i, '0');
                            value[k][index]=resultCopy.get(i).toString();
                            resultCopy.remove(i - 2);
                            resultCopy.remove(i - 2);
                        }
                        index++;
                        i=0;
                        break;
                    case '/':
                        if (resultCopy.get(i - 2) == '1' && resultCopy.get(i - 1) == '0') {
                            resultCopy.set(i, '0');
                            value[k][index]=resultCopy.get(i).toString();
                            resultCopy.remove(i - 2);
                            resultCopy.remove(i - 2);
                        } else {
                            resultCopy.set(i, '1');
                            value[k][index]=resultCopy.get(i).toString();
                            resultCopy.remove(i - 2);
                            resultCopy.remove(i - 2);
                        }
                        index++;
                        i=0;
                        break;
                    case '-':
                        if (resultCopy.get(i - 2) == resultCopy.get(i - 1)) {
                            resultCopy.set(i, '1');
                            value[k][index]=resultCopy.get(i).toString();
                            resultCopy.remove(i - 2);
                            resultCopy.remove(i - 2);
                        } else {
                            resultCopy.set(i, '0');
                            value[k][index]=resultCopy.get(i).toString();
                            resultCopy.remove(i - 2);
                            resultCopy.remove(i - 2);

                        }
                        index++;
                        i=0;
                        break;
                }
            }
            index=0;
        }
        /*for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(value[i][j]+" ");
            }
            System.out.println();
        }*/
        return value;
    }
    String [] getNames(){

        String [] newNames = new String[operations];
        System.arraycopy(names, 0, newNames, 0, operations);
        return newNames;
    }
    private int [][] perestanovki() {
        int len = getNumber();
        int elements[] = {0, 1};

        int els = 2;

        int permutations = (int)Math.pow((double)els, len);

        int  [][]table = new int[permutations][len];

        for (int i = 0; i < len; i++) {
            int t = (int) Math.pow((double)els, i);
            for (int position_i = 0; position_i < permutations;)
                for (int el_num = 0; el_num < els; el_num++)
                    for (int repeats = 0; repeats < t; repeats++) {
                        table[position_i][i] = elements[el_num];
                        position_i++;
                    }
        }
        return table;
    }
    String [][] getPerestanovki(){
        int len=getNumber();
        int els = 2;
        int permutations = (int)Math.pow((double)els, len);
        String [][] mas = new String[permutations][len];
        int [][] mas_i=perestanovki().clone();
        for (int i = 0; i <permutations; i++) {
            for (int j = 0; j < len; j++) {
                mas[i][j]=String.valueOf(mas_i[i][j]);
            }
        }
        return mas;
    }
    String [] getnamesofLatter(){
        String []str = new String[getNumber()];
        char a = 'a';
        for (int i = 0; i < getNumber(); i++) {
            str[i]=Character.toString((char)((int)a +i));
        }
        return str;
    }
     int countIndex(){
        ArrayList<Character> resultCopy = cloneList(toNumbers(0));
        int ind=0;
        for (int i = 0; i <= resultCopy.size() - 1; i++) {
            switch (resultCopy.get(i)) {
                case '!':
                    names[ind]="!" + "A";
                    if (resultCopy.get(i - 1) == '0') {
                        resultCopy.set(i - 1, '1');
                        resultCopy.remove(i);
                    } else {
                        resultCopy.set(i - 1, '0');
                        resultCopy.remove(i);
                    }
                    ind++;
                    i=0;
                    break;
                case '*':
                    names[ind]="A*B";
                    if (resultCopy.get(i - 2) == '1' && resultCopy.get(i - 1) == '1') {
                        resultCopy.set(i, '1');
                        resultCopy.remove(i - 2);
                        resultCopy.remove(i - 2);
                    } else {
                        resultCopy.set(i, '0');
                        resultCopy.remove(i - 2);
                        resultCopy.remove(i - 2);
                    }
                    ind++;
                    i=0;
                    break;
                case '+':
                    names[ind]="A+B";
                    if (resultCopy.get(i - 2) == '1' || resultCopy.get(i - 1) == '1') {
                        resultCopy.set(i, '1');
                        resultCopy.remove(i - 2);
                        resultCopy.remove(i - 2);

                    } else {
                        resultCopy.set(i, '0');
                        resultCopy.remove(i - 2);
                        resultCopy.remove(i - 2);
                    }
                    ind++;
                    i=0;
                    break;
                case '/':
                    names[ind]="A/B";
                    if (resultCopy.get(i - 2) == '1' && resultCopy.get(i - 1) == '0') {
                        resultCopy.set(i, '0');
                        resultCopy.remove(i - 2);
                        resultCopy.remove(i - 2);
                    } else {
                        resultCopy.set(i, '1');
                        resultCopy.remove(i - 2);
                        resultCopy.remove(i - 2);
                    }
                    ind++;
                    i=0;
                    break;
                case '-':
                    names[ind]="A-B";
                    if (resultCopy.get(i - 2) == resultCopy.get(i - 1)) {
                        resultCopy.set(i, '1');
                        resultCopy.remove(i - 2);
                        resultCopy.remove(i - 2);
                    } else {
                        resultCopy.set(i, '0');
                        resultCopy.remove(i - 2);
                        resultCopy.remove(i - 2);

                    }
                    ind++;
                    i=0;
                    break;
            }
        }
        return ind;
    }
}
