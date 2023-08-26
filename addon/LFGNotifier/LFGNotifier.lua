local function OnEvent(self, event, ...)
	Screenshot()
	print(event, ...)
end

local notifierFrame = CreateFrame("Frame")
notifierFrame:RegisterEvent("LFG_PROPOSAL_SHOW")
notifierFrame:SetScript("OnEvent", OnEvent)