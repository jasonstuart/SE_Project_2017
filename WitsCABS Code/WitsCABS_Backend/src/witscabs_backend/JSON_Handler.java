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

//makes sure server can handle JSON files (its basic and my code)
public class JSON_Handler 
{
    //store variable type, name and actual value.
    private String[] varName;
    private String[] varType;
    private String[] value;
    
    //when string comes in, split it correctly for storage
    public JSON_Handler(String input)
    {
        input = input.replace("{", "");
        input = input.replace("}", "");
        String[] temp = input.split("\",|,\"|\",\"");
        
        //allocate memory for correct amount of variables.
        varName = new String[temp.length];
        varType = new String[temp.length];
        value = new String[temp.length];
        
        //insert details into these arrays.
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
    
    //returns value based on string variable created
    public String getValueFromVar(String in)
    {
        //iterate through the list and return value once found
        for(int count = 0; count < varName.length ; count++)
        {
            if(in.equalsIgnoreCase(varName[count]))
            {
                return value[count];
            }
        }
        return null;
    }
    
    //returns the whole JSON file values in order of appearance in one string for SQL use
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
