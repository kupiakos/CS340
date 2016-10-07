#!/usr/bin/env python3

import argparse
import os
import urllib.parse
import requests
from mako.template import Template


# (dest, )
PROPMAPS = {
    'sendchat': ('final String', ' = "SendChat"'),
    'rollnumber': ('final String', ' = "RollNumber"'),
    'robplayer': ('final String', ' = "RobPlayer"'),
    'finishturn': ('final String', ' = "FinishTurn"'),
    'buydevcard': ('final String', ' = "BuyDevCard"'),
    'buildroad': ('final String', ' = "BuildRoad"'),
    'buildsettlement': ('final String', ' = "BuildSettlement"'),
    'buildcity': ('final String', ' = "BuildCity"'),
    'offertrade': ('final String', ' = "OfferTrade"'),
    'accepttrade': ('final String', ' = "AcceptTrade"'),
    'maritimetrade': ('final String', ' = "MaritimeTrade"'),
    'discardcards': ('final String', ' = "DiscardCards"'),

    'soldier': ('final DevCardType', ' = DevCardType.SOLDIER'),
    'monopoly': ('final DevCardType', ' = DevCardType.MONOPOLY'),
    'monument': ('final DevCardType', ' = DevCardType.MONUMENT'),
    'road_building': ('final DevCardType', ' = DevCardType.ROAD_BUILD'),
    'year_of_plenty': ('final DevCardType', ' = DevCardType.YEAR_OF_PLENTY'),

    'Boolean': ('boolean', ''),
}

PROPNAMEMAPS = {
    'playerindex': ('PlayerIndex', ''),
}

MODELPROPMAPS = {
    ('yearofplentyaction', 'resource'): ('ResourceType', ''),
}

MODELPROPNAMEMAPS = {
    ('hex', 'resource'): ('HexType', ''),
    ('hex', 'number'): ('int', ' = 0'),
    ('port', 'resource'): ('PortType', ''),
    ('monopolyaction', 'resource'): ('ResourceType', ''),
    ('maritimetradeaction', 'inputresource'): ('ResourceType', ''),
    ('maritimetradeaction', 'outputresource'): ('ResourceType', ''),
    ('addairequest', 'aitype'): ('AIType', ''),
    ('player', 'color'): ('CatanColor', ''),
    ('port', 'direction'): ('EdgeLocation', ''),
    ('turntracker', 'status'): ('TurnStatus', ''),

    ('offertradeaction', 'receiver'): ('PlayerIndex', ''),
    ('robplayeraction', 'victimIndex'): ('PlayerIndex', ''),
    ('soldieraction', 'victimIndex'): ('PlayerIndex', ''),
    ('vertexobject', 'owner'): ('PlayerIndex', ''),
    ('turntracker', 'currentturn'): ('PlayerIndex', ''),
    ('turntracker', 'longestroad'): ('PlayerIndex', ''),
    ('turntracker', 'largestarmy'): ('PlayerIndex', ''),
    ('clientmodel', 'winner'): ('PlayerIndex', ''),
    ('road', 'owner'): ('PlayerIndex', ''),
    ('tradeoffer', 'sender'): ('PlayerIndex', ''),
    ('tradeoffer', 'receiver'): ('PlayerIndex', ''),
    ('messageline', 'source'): ('PlayerIndex', ''),
}

MODEL_NAME = {
    'sendchat': 'SendChatAction',
    'rollnumber': 'RollNumberAction',
    'robplayer': 'RobPlayerAction',
    'finishmove': 'FinishMoveAction',
    'buydevcard': 'BuyDevCardAction',
    'year_of_plenty_': 'YearofPlentyAction',
    'road_building_': 'RoadBuildingAction',
    'soldier_': 'SoldierAction',
    'monopoly_': 'MonopolyAction',
    'monument_': 'MonumentAction',
    'buildroad': 'BuildRoadAction',
    'buildsettlement': 'BuildSettlementAction',
    'buildcity': 'BuildCityAction',
    'offertrade': 'OfferTradeAction',
    'accepttrade': 'AcceptTradeAction',
    'maritimetrade': 'MaritimeTradeAction',
    'discardcards': 'DiscardCardsAction',
}

EXCLUDE_MODELS = [
    'CatanColor',
    'HexType',
    'PieceType',
    'PortType',
    'ResourceType',
    'EdgeDirection',
    'EdgeLocation',
    'HexLocation',
    'VertexDirection',
    'VertexLocation',
]

INCLUDES = {
    'AIType': 'shared.definitions.AIType',
    'DevCardType': 'shared.definitions.DevCardType',
    'PlayerIndex': 'shared.definitions.PlayerIndex',
    'CatanColor': 'shared.definitions.CatanColor',
    'HexType': 'shared.definitions.HexType',
    'PieceType': 'shared.definitions.PieceType',
    'PortType': 'shared.definitions.PortType',
    'ResourceType': 'shared.definitions.ResourceType',
    'TurnStatus': 'shared.definitions.TurnStatus',

    'EdgeDirection': 'shared.locations.EdgeDirection',
    'EdgeLocation': 'shared.locations.EdgeLocation',
    'HexLocation': 'shared.locations.HexLocation',
    'VertexDirection': 'shared.locations.VertexDirection',
    'VertexLocation': 'shared.locations.VertexLocation',

    'List': ['java.util.ArrayList', 'java.util.List']
}

VERBS = ['has', 'is', 'will', 'wont', 'can']


class Model:
    def __init__(self, data: dict):
        self.name = data['id']
        if self.name.lower() in MODEL_NAME:
            self.name = MODEL_NAME[self.name.lower()]
        print('Processing model', self.name)
        self.docs = data.get('description')
        self.properties = [
            Property(name, self, data) for name, data in data['properties'].items()
        ]
        self.includes = set()
        for prop in self.properties:
            r = INCLUDES.get(prop.javatype.split('<')[0].replace('final', '').strip())
            if r is not None:
                if isinstance(r, list):
                    for i in r:
                        self.includes.add(i)
                else:
                    self.includes.add(r)


class Property:
    def __init__(self, name: str, model: Model, data: dict):
        assert name
        self.name = name
        print('  Processing property', self.name)
        # Private properties start with lowercase
        self.serial_name = name
        self.name = self.name[0].lower() + self.name[1:]

        # Add documentation
        self.docs = data.get('description', 'The ' + name)

        # The datatype can be harder
        json_type = data['type']
        self.init = ''
        self.can_set = True
        if json_type.lower() in PROPMAPS:
            self.javatype, self.init = PROPMAPS[json_type.lower()]
        elif (model.name.lower(), self.name.lower()) in MODELPROPNAMEMAPS:
            self.javatype, self.init = MODELPROPNAMEMAPS[(model.name.lower(), self.name.lower())]
        elif (model.name.lower(), json_type.lower()) in MODELPROPMAPS:
            self.javatype, self.init = MODELPROPMAPS[(model.name.lower(), json_type.lower())]
        elif self.name.lower() in PROPNAMEMAPS:
            self.javatype, self.init = PROPNAMEMAPS[self.name.lower()]
        elif json_type == 'array':
            internal_type = data['items']['$ref']
            self.javatype = 'List<{}>'.format(internal_type)
            self.init = ' = new ArrayList<{}>()'.format(internal_type)
        elif json_type == 'index' or json_type == 'integer':
            self.javatype = 'int'
        elif json_type == 'boolean':
            self.javatype = 'boolean'
        elif json_type == 'string':
            self.javatype = 'String'
        else:
            self.javatype = json_type[0].upper() + json_type[1:]

        # If the type contains, final, then it can't be set or deserialized
        self.can_set = 'final' not in self.javatype
        cap_name = self.name[0].upper() + self.name[1:]
        if self.javatype == 'boolean' and not any(self.name.startswith(i) for i in VERBS):
            self.getter = 'is' + cap_name
        else:
            self.getter = 'get' + cap_name
        self.setter = 'set' + cap_name
        self.wither = 'with' + cap_name


def process_api(template: Template, url: str, out_dir: str, package_name: str) -> None:
    print('Processing API', url)
    r = requests.get(url)
    if not r.ok:
        return
    data = r.json()
    if not os.path.isdir(out_dir):
        os.mkdir(out_dir)

    models = [
        Model(m)
        for n, m in data.get('models', {}).items()
        if n not in EXCLUDE_MODELS
    ]

    for model in models:
        with open(os.path.join(out_dir, model.name + '.java'), 'w') as model_file:
            model_file.write(template.render(model=model, package=package_name))

    for api in data.get('apis', []):
        sub_name = api['path'].split('/')[-1]
        sub_package = package_name + '.' + sub_name
        sub_dir = os.path.join(out_dir, sub_name)
        sub_url = urllib.parse.urljoin(url if url.endswith('/') else url + '/', sub_name)
        process_api(template, sub_url, sub_dir, sub_package)


def main():
    parser = argparse.ArgumentParser(description='Render models from swagger')
    parser.add_argument('-u', '--url', required=True, help='The API doc to read from')
    parser.add_argument('-o', '--out-dir', required=True, help='Where to write the generated model names')
    parser.add_argument('-p', '--package-name', default='models', help='The package name to write to')
    parser.add_argument('-t', '--template', required=True, help='The Mako template file to generate with')
    args = parser.parse_args()
    process_api(Template(filename=args.template), args.url, args.out_dir, args.package_name)


if __name__ == '__main__':
    main()
