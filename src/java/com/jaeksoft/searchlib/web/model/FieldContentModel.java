/**   
 * License Agreement for Jaeksoft SearchLib Community
 *
 * Copyright (C) 2008-2009 Emmanuel Keller / Jaeksoft
 * 
 * http://www.jaeksoft.com
 * 
 * This file is part of Jaeksoft SearchLib Community.
 *
 * Jaeksoft SearchLib Community is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 * Jaeksoft SearchLib Community is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Jaeksoft SearchLib Community. 
 *  If not, see <http://www.gnu.org/licenses/>.
 **/

package com.jaeksoft.searchlib.web.model;

import org.zkoss.zkplus.databind.BindingListModel;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Panelchildren;
import org.zkoss.zul.event.ListDataListener;

import com.jaeksoft.searchlib.index.FieldContent;

public class FieldContentModel implements BindingListModel {

	private FieldContent fieldContent;

	protected FieldContentModel(FieldContent fieldContent) {
		this.fieldContent = fieldContent;
	}

	@Override
	public int indexOf(Object data) {
		int i = 0;
		for (String value : fieldContent.getValues())
			if (value == data)
				return i;
			else
				i++;
		return -1;
	}

	@Override
	public void addListDataListener(ListDataListener arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object getElementAt(int index) {
		return fieldContent.getValues().get(index);
	}

	@Override
	public int getSize() {
		return fieldContent.getValues().size();
	}

	@Override
	public void removeListDataListener(ListDataListener arg0) {
		// TODO Auto-generated method stub
	}

	public String getField() {
		return fieldContent.getField();
	}

	public static Panelchildren createIndexDocumentComponent(
			FieldContent[] fieldContents) {
		Panelchildren component = new Panelchildren();
		for (FieldContent fieldContent : fieldContents) {
			Panel panel = new Panel();
			panel.setTitle(fieldContent.getField());
			panel.setCollapsible(true);
			Panelchildren panelchildren = new Panelchildren();
			Grid grid = new Grid();
			grid.setMold("paging");
			grid.setModel(new FieldContentModel(fieldContent));
			grid.setParent(panelchildren);
			panelchildren.setParent(panel);
			panel.setParent(component);
		}
		return component;
	}
}
