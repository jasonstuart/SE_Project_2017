/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package witscabs_backend;

/**
 *
 * @author Jason
 */
public class JSON_Handler 
{
    private String[] varName;
    private String[] varType;
    private String[] value;
    
    public JSON_Handler(String input)
    {
        input = input.replace("{", "");
        input = input.replace("}", "");
        String[] temp = input.split("\",|,\"|\",\"");
        
        varName = new String[temp.length];
        varType = new String[temp.length];
        value = new String[temp.length];
        for(int count = 0; count < temp.length ; count++)
        {
            if(temp[count].length()-temp[count].replace("\"", "").length() == 2 && temp[count].charAt(0) == '\"')
            {
                varType[count] = "int";
            }
            else
            {
                varType[count] = "String";
            }
            
            temp[count] = temp[count].replace("\"", "");
            varName[count] = temp[count].split(":")[0];
            value[count] = temp[count].split(":")[1];
        }
    }
    
    public String getValueFromVar(String in)
    {
        for(int count = 0; count < varName.length ; count++)
        {
            if(in.equalsIgnoreCase(varName[count]))
            {
                return value[count];
            }
        }
        return null;
    }
    
    public String getWholeValue()
    {
        String temp = "";
        for(int c = 0 ; c < value.length-1 ; c++)
        {
            if(varType[c].equalsIgnoreCase("String"))
            {
                temp += "\"" + value[c] + "\",";                
            }
            else
            {
                temp += "" + value[c] + ",";
            }
        }
        if(varType[value.length-1].equalsIgnoreCase("String"))
        {
            temp+= "\""+value[value.length-1]+"\"";
        }
        else
        {
            temp+= value[value.length-1];
        }
        
        return temp;
    }
}
