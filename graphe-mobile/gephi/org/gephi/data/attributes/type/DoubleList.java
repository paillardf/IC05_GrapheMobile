/*
Copyright 2008-2010 Gephi
Authors : Martin Škurla <bujacik@gmail.com>
Website : http://www.gephi.org

This file is part of Gephi.

Gephi is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

Gephi is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with Gephi.  If not, see <http://www.gnu.org/licenses/>.
*/
package org.gephi.data.attributes.type;

/**
 * Complex type that define a list of Double items. Can be created from a double
 * array, from a Double array or from single string using either given or default separators.
 * 
 * @author Martin Škurla
 */
public final class DoubleList extends AbstractList<Double> {//NumberList<Double> {

    public DoubleList(double[] primitiveDoubleArray) {
        create(primitiveDoubleArray);
    }

    public DoubleList(Double[] wrapperDoubleArray) {
        create(wrapperDoubleArray);
    }

    public DoubleList(String input) {
        this(input, AbstractList.DEFAULT_SEPARATOR);
    }

    public DoubleList(String input, String separator) {
        //super(input, separator, Double.class);
    	String[] values=input.split(separator);
    	for (int p=0;p<values.length;p++)
    		this.list[p]=Double.parseDouble(values[p]);
    }
    
    public void create(Double[] liste) {
    	this.list=new Double[liste.length];
    	for (int p=0;p<list.length;p++)
    		this.list[p]=liste[p];
    }
    public void create(double[] liste) {
    	this.list=new Double[liste.length];
    	for (int p=0;p<list.length;p++)
    		this.list[p]=liste[p];
    }
    
}
