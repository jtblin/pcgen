/*
 * Copyright 2010 (C) Thomas Parker <thpr@users.sourceforge.net>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package plugin.primitive.spell;

import java.util.HashSet;
import java.util.Set;

import pcgen.cdom.enumeration.GroupingState;
import pcgen.cdom.enumeration.ListKey;
import pcgen.core.Globals;
import pcgen.core.PlayerCharacter;
import pcgen.core.spell.Spell;
import pcgen.rules.context.LoadContext;
import pcgen.rules.persistence.token.PrimitiveToken;

public class DescriptorToken implements PrimitiveToken<Spell>
{
	private static final Class<Spell> SPELL_CLASS = Spell.class;
	private String school;

	public boolean initialize(LoadContext context, Class<Spell> cl,
		String value, String args)
	{
		if (args != null)
		{
			return false;
		}
		school = value;
		return true;
	}

	public String getTokenName()
	{
		return "DESCRIPTOR";
	}

	public Class<Spell> getReferenceClass()
	{
		return SPELL_CLASS;
	}

	public String getLSTformat()
	{
		return getTokenName() + "=" + school;
	}

	public boolean allow(PlayerCharacter pc, Spell spell)
	{
		return spell.containsInList(ListKey.SPELL_DESCRIPTOR, school);
	}

	public Set<Spell> getSet(PlayerCharacter pc)
	{
		HashSet<Spell> spellSet = new HashSet<Spell>();
		for (Spell spell : Globals.getContext().ref
			.getConstructedCDOMObjects(SPELL_CLASS))
		{
			if (allow(pc, spell))
			{
				spellSet.add(spell);
			}
		}
		return spellSet;
	}

	public GroupingState getGroupingState()
	{
		return GroupingState.ANY;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == this)
		{
			return true;
		}
		if (obj instanceof DescriptorToken)
		{
			DescriptorToken other = (DescriptorToken) obj;
			return school.equals(other.school);
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		return school == null ? -7 : school.hashCode();
	}

}
